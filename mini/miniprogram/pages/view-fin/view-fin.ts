import { viewFin, getTodayStr } from '../../utils/util'
Page({

  data: {
    safeTop: 0,
    openId: "",
    token: "",
    user: {
      start: "",
      end: "",
    },
    chargeList:[],
    historyList:[],
    total: "",

  },

  async confirm(){
    const url = "/fin/search";
    const data:any = {};
    data.start = this.data.user.start;
    data.end = this.data.user.end;
    try {
      const viewFinRes = await viewFin(url, this.data.openId, this.data.token, data);
      this.setData({"chargeList": viewFinRes.chargeList, "historyList": viewFinRes.historyList, "total": viewFinRes.total})
    } catch(e) {
      wx.showToast({ title: '检查日期格式!', duration: 1000, icon: 'error' });
    }
  },
  clearData: function () {
    const userInfo: any = this.data.user;
    const newUserInfo: any = {};
    for (let key in userInfo) {
      if (userInfo.hasOwnProperty(key)) {
        newUserInfo[key] = "";
      }
    }
    this.setData({user: newUserInfo, chargeList:[], historyList:[], total: ""});
    this.init();
  },

  onStartChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.start": value, });
  },

  onEndChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.end": value, });
  },

  init() {
    const currDay = getTodayStr()
    this.setData({"user.start": currDay, "user.end": currDay});
  },
  onLoad() {
    this.init();
  },
  onReady() {

  },

  onShow() {
    const openId = wx.getStorageSync("openId");
    const token = wx.getStorageSync("token");
    this.setData({openId: openId,token: token});
  },

  onHide() {

  },

  onUnload() {

  },

  onPullDownRefresh() {

  },

  onReachBottom() {

  },

  onShareAppMessage() {

  }
})