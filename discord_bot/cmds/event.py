import discord
from discord.ext import commands 
import json
from core.classes import Cog_Extension

with open('items.json', "r", encoding = "utf8") as file:
    data = json.load(file) 

class Event(Cog_Extension):
    @commands.Cog.listener()
    async def on_message(self, message):
        keyword=['apple','banana','chocolate']
        if message.content == "gg":
            print("qq")
            await message.channel.send('ez')   #擷取訊息，若訊息裡含有特定字，則回傳特定訊息
        if message.content == "ac":
            await message.delete()   #刪除訊息
        if message.content.endswith("apple") and message.author != self.bot.user :
            await message.channel.send("apple")
        if message.content in keyword:
            await message.channel.send("delicious")


    @commands.Cog.listener()
    async def on_member_join(self, member):
        await self.bot.get_channel(int(data["channel_id"])).send( f"哈囉 <@{member.id}> 歡迎加入可憐那 ")  #成員加入

    @commands.Cog.listener()
    async def on_member_remove(self, member):
        await self.bot.get_channel(int(data["channel_id"])).send(f"<@{member.id}> 已離開可憐那")  #成員離開

async def setup(bot):
    await bot.add_cog(Event(bot))