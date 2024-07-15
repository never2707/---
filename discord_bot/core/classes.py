import discord
from discord.ext import commands 
import wavelink

class Cog_Extension(commands.Cog):
    def __init__(self,bot):
        self.bot = bot

class CustomPlayer(wavelink.Player):

    def __init__(self):
        super().__init__()
        self.queue = wavelink.Queue()

async def setup(bot):
    await bot.add_cog(Cog_Extension(bot))