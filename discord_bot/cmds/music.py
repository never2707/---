import asyncio
import discord
from discord.ext import commands
import yt_dlp
from core.classes import Cog_Extension
from pytube import Playlist
import datetime
from random import randint
import requests
from bs4 import BeautifulSoup
import random
import json

with open('items.json', "r", encoding = "utf8") as file:
    data = json.load(file)

yt_dlp.utils.bug_reports_message = lambda: ''

ytdl_format_options = {
    'format': 'bestaudio/best',
    'outtmpl': '%(title)s.%(ext)s',
    'noplaylist': True,
    'nocheckcertificate': True,
    'ignoreerrors': False,
    'logtostderr': False,
    'no_warnings': True,
    'default_search': 'auto',
    'source_address': '0.0.0.0',
    'configlocationsPATH' : '.\\music_cache'
     # bind to ipv4 since ipv6 addresses cause issues sometimes
}

ffmpeg_options = {'options': '-vn'}

ytdl = yt_dlp.YoutubeDL(ytdl_format_options)

class YTDLSource(discord.PCMVolumeTransformer):
    def __init__(self, source, *, data, volume=0.5):
        super().__init__(source, volume)

        self.data = data

        self.title = data.get('title')
        self.url = data.get('url')

    @classmethod
    async def from_url(cls, url, *, loop=None, stream=False ,):
        loop = loop or asyncio.get_event_loop()
        data = await loop.run_in_executor(None, lambda: ytdl.extract_info(url, download=not stream))

        if 'entries' in data:
            # take first item from a playlist
            data = data['entries'][0]

        filename = data['url'] if stream else ytdl.prepare_filename(data)
        return cls(discord.FFmpegPCMAudio(filename, **ffmpeg_options), data=data)

def is_connected(ctx):
    voice_client = ctx.message.guild.voice_client
    return voice_client and voice_client.is_connected()

queue = []

loop = False

voice_clients = {}

class Music(Cog_Extension):

    @commands.command(name='loop', help='Change loop mode')
    async def loop_(self,ctx):
        global loop

        if loop:
            await ctx.send('Loop mode is now `False!`')
            loop = False
        
        else: 
            await ctx.send('Loop mode is now `True!`')
            loop = True

    @commands.command(name='random', help='Change random mode')
    async def random(self,ctx):
        random.shuffle(queue)
        await ctx.send("Your queue has shuffled.")
        

    @commands.command(name='play', help='This command plays music')
    async def play(self,ctx):
        global queue
        global loop

        if not ctx.message.author.voice:
            await ctx.send("You are not connected to a voice channel")
            return
        
        elif len(queue) == 0:
            queue.append(random.choice(data["automusic"]))
            channel = ctx.message.author.voice.channel
            await channel.connect()

        else:
            try:
                channel = ctx.message.author.voice.channel
                await channel.connect()
            except: 
                pass

        server = ctx.message.guild
        voice_channel = server.voice_client


        while queue:
            try:
                while voice_channel.is_playing() or voice_channel.is_paused():
                    await asyncio.sleep(2)
                    pass

            except AttributeError:
                pass
            
            try:
                async with ctx.typing():

                    player = await YTDLSource.from_url(queue[0], loop=loop)
                    voice_channel.play(player, after=lambda e: print('Player error: %s' % e) if e else None)
                        
                    if loop == True:
                        queue.append(queue[0])

                    del(queue[0])

                    if len(queue) == 0:
                        queue.append(random.choice(data["automusic"]))
                        
                await ctx.send('**Now playing:** {}'.format(player.title))

            except:
                break

    @commands.command(name='volume', help='This command changes the bots volume')
    async def volume(self, ctx, volume: int):
        if ctx.voice_client is None:
            return await ctx.send("Not connected to a voice channel.")
        
        ctx.voice_client.source.volume = volume / 100
        await ctx.send(f"Changed volume to {volume}%")


    @commands.command(name='pause', help='This command pauses the song')
    async def pause(self,ctx):
        server = ctx.message.guild
        voice_channel = server.voice_client

        voice_channel.pause()


    @commands.command(name='resume', help='This command resumes the song!')
    async def resume(self,ctx):
        server = ctx.message.guild
        voice_channel = server.voice_client

        voice_channel.resume()


    @commands.command(name='stop', help='This command stops the song!')
    async def skip(self,ctx):
        server = ctx.message.guild
        voice_channel = server.voice_client

        voice_channel.stop()


    @commands.command(name='queue')
    async def queue(self,ctx, *, url):
        global queue

        queue.append(url)
        await ctx.send(f'`{url}` added to queue!')


    @commands.command()
    async def playlist(self,ctx,arg):
        p = Playlist(str(arg))
        for i in p.video_urls:
            queue.append(i)
        await ctx.send("Your urls are added.")


    @commands.command(name='remove')
    async def remove(self,ctx, number):     
        try:
            del(queue[int(number)])
        
        except:
            await ctx.send('Your queue is either **empty** or the index is **out of range**')


    @commands.command(name='view', help='This command shows the queue')
    async def view(self,ctx):
        global queue

        if len(queue) > 10:
            k=10
        else:
            k=len(queue)
        des = ""
        for i in range(k):
            url = queue[i]
            reqs = requests.get(url)
            soup = BeautifulSoup(reqs.text, 'html.parser')
            json = soup.find('title').text
            des += f"{i+1}. {json}"
            des += "\n"
        embed=discord.Embed(title=f"Next {k} Songs", description=des, color=0x00fffb, timestamp=datetime.datetime.now())
        embed.set_author(name="NOT A BOT", icon_url="https://memeprod.ap-south-1.linodeobjects.com/user-template/af7e59a4e58c1ff7c6862f03724f5e86.png")
        await ctx.send(embed=embed)

async def setup(bot):
    await bot.add_cog(Music(bot))