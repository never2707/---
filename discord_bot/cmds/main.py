import discord
from discord.ext import commands 
from core.classes import Cog_Extension

class Main(Cog_Extension):

    @commands.command()
    async def ping(self,ctx):
        """抓ping值"""
        await ctx.send(f"{round(self.bot.latency*1000)}(ms)")  #傳送ping值

    @commands.command()
    async def hello(self,ctx):
        """跟你打招呼"""
        await ctx.send(f"Hi <@{ctx.author.id}>")  #跟你打招呼


async def setup(bot):
    await bot.add_cog(Main(bot))