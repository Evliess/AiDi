// pages/charge-vip/charge-vip.ts
import { app } from '../../app';
import { openId, token, addCharge, findVipByPhone } from '../../utils/util'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    safeTop: 0,
    selectedValue: '', // 默认选中的值
    items: [
      { name: '次卡', value: 'day' },
      { name: '月卡', value: 'month' },
      { name: '季卡', value: 'season' },
      { name: '年卡', value: 'year' }
    ],
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
    }
  },

  async confirm(){
    try {
      const data:any = {};
      data.phone = this.data.user.phone;
      if(!data.phone) {
        wx.showToast({ title: '请检查手机号!', duration: 1000, icon: 'error' });
        return;
      }
      data.money = this.data.user.money;
      if(!data.money) {
        wx.showToast({ title: '请检查金额!', duration: 1000, icon: 'error' });
        return;
      }
      data.name = this.data.user.name;
      data.type = this.data.selectedValue;
      data.leftCount = this.data.user.leftCount;
      data.expiredAt = this.data.user.expiredAt;
      const resAddVip = await addCharge("/consumers-charge", openId, token, data);
      if (resAddVip.status != "ok") {
        wx.showToast({ title: '请检查手机号!', duration: 1000, icon: 'error' });
        return;
      }
      wx.showToast({ title: '充值成功!', duration: 1000, icon: 'success' });
    } catch (e) {
      wx.showToast({ title: '请检查手机号!', duration: 1000, icon: 'error' });
      return;
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
    this.setData({user: newUserInfo});
  },
  async find(){
    try{
      const url = "/consumer/" + this.data.user.phone;
      const findVipByPhoneRes = await findVipByPhone(url, openId, token);
      if(findVipByPhoneRes.status=="ng") {
        this.setData({"user.name": null});
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
    } catch(e) {
      console.error(e);
      wx.showToast({ title: '请检查手机号!', duration: 1000, icon: 'error' });
      return;
    }
  },

  onPhoneChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.phone": value, });
  },
  onMoneyChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.money": value, });
  },
  onLeftCountChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.leftCount": value, });
  },
  onExpiredAtChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.expiredAt": value, })
  },
  onRadioChange: function(e: any) {
    const selectedValue = e.detail.value;
    this.setData({
      selectedValue: selectedValue
    });
  },
  onLoad: function() {
    const safeTop = app.globalData.safeTop;
    this.setData({
      safeTop: safeTop,
      selectedValue: this.data.items[0].value
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