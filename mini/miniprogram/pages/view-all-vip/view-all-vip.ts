// pages/view-all-vip/view-all-vip.ts
import { viewAllVipOrdered } from '../../utils/util'
Page({
  data: {
    openId: "",
    token: "",
    allVipOrdered : []
  },

  onLoad() {
    const openId = wx.getStorageSync("openId");
    const token = wx.getStorageSync("token");
    this.setData({openId: openId,token: token});
  },

  onReady() {

  },
  async onShow() {
    try {
      const url = "/consumer/view-all-ordered";
      const viewAllVipOrderedRes = await viewAllVipOrdered(url,this.data.openId, this.data.token);
      console.log(viewAllVipOrderedRes)
      this.setData({allVipOrdered: viewAllVipOrderedRes});
    } catch(e) {}
  },
  onHide() {},

  onUnload() {

  },

  onPullDownRefresh() {

  },

  onReachBottom() {

  },

  onShareAppMessage() {

  }
})