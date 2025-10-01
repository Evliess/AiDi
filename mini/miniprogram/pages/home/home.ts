// home.ts
Page({
  data: {
    safeTop: 0,
    token: "",
  },
  addVip: function(){wx.navigateTo({"url": "/pages/add-vip/add-vip"});},
  addNonVip: function(){wx.navigateTo({"url": "/pages/non-vip/non-vip"});},
  chargeVip: function(){wx.navigateTo({"url": "/pages/charge-vip/charge-vip"});},
  playVip: function(){wx.navigateTo({"url": "/pages/play-vip/play-vip"});},
  viewVip: function() {wx.navigateTo({"url": "/pages/view-vip/view-vip"});},
  viewFin: function() {wx.navigateTo({"url": "/pages/view-fin/view-fin"});},
  viewAllVip: function() {wx.navigateTo({"url": "/pages/view-all-vip/view-all-vip"});},
  onLoad: function() {
    const token = wx.getStorageSync("token");
    if(token.length>0) this.setData({token: token});
  },
  
})
