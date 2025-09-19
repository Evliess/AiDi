// home.ts
import { app } from '../../app'; 
Page({
  data: {
    safeTop: 0,
  },
  addVip: function(){wx.navigateTo({"url": "/pages/add-vip/add-vip"});},
  addNonVip: function(){wx.navigateTo({"url": "/pages/non-vip/non-vip"});},
  chargeVip: function(){wx.navigateTo({"url": "/pages/charge-vip/charge-vip"});},
  playVip: function(){wx.navigateTo({"url": "/pages/play-vip/play-vip"});},
  onLoad: function() {
    const safeTop = app.globalData.safeTop;
    this.setData({
      safeTop: safeTop
    });
  },
})
