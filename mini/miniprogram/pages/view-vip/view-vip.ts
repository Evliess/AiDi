import { viewVip } from '../../utils/util'
Page({

  data: {
    openId: "",
    token: "",
    user: {
      name: "",
      phone: "",
      money: "",
      type: "",
      score: "",
      chineseType: "",
      oldType: "",
      leftCount: "",
      oldLeftCount: "",
      expiredAt: "",
      oldExpiredAt: "",
      oldChargeAt: "",
    },
    playLists: [],
    chargeList: [],
    chargeTotal:"",
  },

  onPhoneChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.phone": value, });
  },

  clearData: function () {
    const userInfo: any = this.data.user;
    const newUserInfo: any = {};
    for (let key in userInfo) {
      if (userInfo.hasOwnProperty(key)) {
        newUserInfo[key] = "";
      }
    }
    this.setData({user: newUserInfo, playLists:[], chargeLists:[], chargeTotal: ""});
  },
  async find(){
    try{
      const url = "/consumer/view-info/" + this.data.user.phone;
      const findVipByPhoneRes = await viewVip(url, this.data.openId, this.data.token);
      if(findVipByPhoneRes.status=="ng") {
        this.setData({"user.name": null, playLists:[], chargeLists:[]});
        wx.showToast({ title: findVipByPhoneRes.message, duration: 1000, icon: 'error' });
        return;
      }
      this.setData({"user.name":findVipByPhoneRes.name,
      "user.oldChargeAt": findVipByPhoneRes.chargeAt,
      "user.oldType": findVipByPhoneRes.type,
      });
      if(findVipByPhoneRes.score) {
        this.setData({"user.score": findVipByPhoneRes.score});
      } else {
        this.setData({"user.score": null});
      }
      if(findVipByPhoneRes.type=="day") this.setData({"user.chineseType": "次卡"});
      if(findVipByPhoneRes.type=="month") this.setData({"user.chineseType": "月卡"});
      if(findVipByPhoneRes.type=="season") this.setData({"user.chineseType": "季卡"});
      if(findVipByPhoneRes.type=="year") this.setData({"user.chineseType": "年卡"});
      if(findVipByPhoneRes.type!="day") {
        this.setData({"user.oldExpiredAt": findVipByPhoneRes.expiredAt});
      } else {
        this.setData({"user.oldLeftCount": findVipByPhoneRes.leftCount});
      }
      this.setData({"playLists": findVipByPhoneRes.playLists, "chargeLists": findVipByPhoneRes.chargeLists, "chargeTotal": findVipByPhoneRes.chargeTotal});
    } catch(e) {
      wx.showToast({ title: '请检查会员号!', duration: 1000, icon: 'error' });
      return;
    }
  },
  onLoad() {
    const openId = wx.getStorageSync("openId");
    const token = wx.getStorageSync("token");
    this.setData({
      openId: openId,
      token: token
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})