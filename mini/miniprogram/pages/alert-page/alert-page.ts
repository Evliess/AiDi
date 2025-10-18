// pages/alert-page/alert-page.ts
Page({
  
  from: "",
  data: {
    label: "",
    res: "",
    msg: "",
  },
  backHome() {
    wx.redirectTo({"url": "/pages/home/home"});
  },

  continue() {
    wx.redirectTo({"url": "/pages/"+this.from+"/"+this.from});
  },

  onLoad(options: any) {
    this.from = options.from;
    this.data.res = options.res;
    this.data.msg = options.msg;
    let tmp = "";
    if(this.from=="add-vip") tmp = "继续添加";
    if(this.from=="charge-vip") tmp = "继续充值";
    if(this.from=="play-vip" || this.from=="non-vip") tmp = "继续划卡";
    this.setData({"res": options.res, "msg": options.msg, "label": tmp});
  },

  onReady() {},
  onShow() {},
  onHide() {},
  onUnload() {},
  onPullDownRefresh() {},
  onReachBottom() {},
  onShareAppMessage() {}
})