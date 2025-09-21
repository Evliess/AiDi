import { app } from '../../app';
import { openId, token, nonVipPlay } from '../../utils/util'
Page({
  data: {
    safeTop: 0,
    user: {
      money: "",
      itemName: "",
    }
  },

  onMoneyChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.money": value, });
  },

  onItemChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.itemName": value, });
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

  async confirm() {
    const url = "/non-consumers";
    const data:any = {};
    data.money = this.data.user.money;
    data.itemName = this.data.user.itemName;
    try {
      await nonVipPlay(url, openId, token, data);
      wx.showToast({ title: '成功!', duration: 1000, icon: 'success' });
    } catch(e) {
      wx.showToast({ title: '出错了!', duration: 1000, icon: 'error' });
    }
  },
  onLoad: function() {
    const safeTop = app.globalData.safeTop;
    this.setData({
      safeTop: safeTop
    });
  },
})
