import discord
from discord.ext import commands 
from core.classes import Cog_Extension
import yfinance as yf 
import datetime

dic1={"day":"1d","comp":"2330.TW"}

class Stock(Cog_Extension):

    @commands.command()
    async def day(self,ctx,arg):
        """設定要取幾天前的資料"""
        dic1["day"] = str(arg)            
        embed=discord.Embed(title="要取的天數", description=dic1["day"], color=0x00fffb, timestamp=datetime.datetime.now())
        embed.set_author(name="NOT A BOT", icon_url="https://memeprod.ap-south-1.linodeobjects.com/user-template/af7e59a4e58c1ff7c6862f03724f5e86.png")
        await ctx.send(embed=embed)    #設定要取n天前的資料

    @commands.command()
    async def comp(self,ctx,arg):
        """設定你要取的上市股票"""
        dic1["comp"] = str(arg)
        embed=discord.Embed(title="所選的上市公司", description=dic1["comp"], color=0x00fffb, timestamp=datetime.datetime.now())
        embed.set_author(name="NOT A BOT", icon_url="https://memeprod.ap-south-1.linodeobjects.com/user-template/af7e59a4e58c1ff7c6862f03724f5e86.png")
        await ctx.send(embed=embed)

    @commands.command()
    async def open(self,ctx):
        """抓開盤價"""
        df=yf.Ticker(dic1["comp"]).history (period=str(dic1["day"]))
        df_new=df.head(1)
        df_new.index=["1"]
        price=(df_new.at["1","Open"])  #n天前開盤價
        comp=dic1["comp"]
        embed=discord.Embed(title=f"{comp}的開盤價", description=price, color=0x00fffb, timestamp=datetime.datetime.now())
        embed.set_author(name="NOT A BOT", icon_url="https://memeprod.ap-south-1.linodeobjects.com/user-template/af7e59a4e58c1ff7c6862f03724f5e86.png")
        await ctx.send(embed=embed)

    @commands.command()
    async def high(self,ctx,):
        """抓高點"""
        df=yf.Ticker(dic1["comp"]).history (period=str(dic1["day"]))
        df_new=df.head(1)
        df_new.index=["1"]
        price=(df_new.at["1", "High"])  #n天前高點
        comp=dic1["comp"]
        embed=discord.Embed(title=f"{comp}的高點", description=price, color=0x00fffb, timestamp=datetime.datetime.now())
        embed.set_author(name="NOT A BOT", icon_url="https://memeprod.ap-south-1.linodeobjects.com/user-template/af7e59a4e58c1ff7c6862f03724f5e86.png")
        await ctx.send(embed=embed)

    @commands.command()
    async def low(self,ctx,):
        """抓低點"""
        df=yf.Ticker(dic1["comp"]).history (period=str(dic1["day"]))
        df_new=df.head(1)
        df_new.index=["1"]
        price=(df_new.at["1", "Low"])  #n天前低點
        comp=dic1["comp"]
        embed=discord.Embed(title=f"{comp}的低點", description=price, color=0x00fffb, timestamp=datetime.datetime.now())
        embed.set_author(name="NOT A BOT", icon_url="https://memeprod.ap-south-1.linodeobjects.com/user-template/af7e59a4e58c1ff7c6862f03724f5e86.png")
        await ctx.send(embed=embed)

    @commands.command()
    async def close(self,ctx,):
        """抓收盤價"""
        df=yf.Ticker(dic1["comp"]).history (period=str(dic1["day"]))
        df_new=df.head(1)
        df_new.index=["1"]
        price=(df_new.at["1", "Close"])  #n天前收盤價
        comp=dic1["comp"]
        embed=discord.Embed(title=f"{comp}的收盤價", description=price, color=0x00fffb, timestamp=datetime.datetime.now())
        embed.set_author(name="NOT A BOT", icon_url="https://memeprod.ap-south-1.linodeobjects.com/user-template/af7e59a4e58c1ff7c6862f03724f5e86.png")
        await ctx.send(embed=embed)

    @commands.command()
    async def volumes(self,ctx,):
        """抓成交量"""
        df=yf.Ticker(dic1["comp"]).history (period=str(dic1["day"]))
        df_new=df.head(1)
        df_new.index=["1"]
        price=(df_new.at["1", "Volume"]) #n天前成交量
        comp=dic1["comp"]
        embed=discord.Embed(title=f"{comp}的成交量", description=price, color=0x00fffb, timestamp=datetime.datetime.now())
        embed.set_author(name="NOT A BOT", icon_url="https://memeprod.ap-south-1.linodeobjects.com/user-template/af7e59a4e58c1ff7c6862f03724f5e86.png")
        await ctx.send(embed=embed)

async def setup(bot):
    await bot.add_cog(Stock(bot))