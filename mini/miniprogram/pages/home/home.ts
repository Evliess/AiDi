// home.ts
const app = getApp()
Page({
  data: {
    safeTop: 0,
  },
  addVip: function(){
    wx.navigateTo({"url": "/pages/add-vip/add-vip"});
  },
  addNonVip: function(){
    wx.navigateTo({"url": "/pages/non-vip/non-vip"});
  },
  onLoad: function() {
    const safeTop = app.globalData.safeTop;
    this.setData({
      safeTop: safeTop
    });
  },
})
