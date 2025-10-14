// pages/add-vip/add-vip.ts
import { addVip, getTodayStr } from '../../utils/util'

Page({
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
      leftCount: "",
      expiredAt: "",
      chargeAt: "",
      memo: "",
    },
    openId: "",
    token: "",
  },
  onRadioChange: function (e: any) {
    const selectedValue = e.detail.value;
    if (selectedValue == 'day') {
      this.setData({ selectedValue: selectedValue, "user.expiredAt": "" });
    } else {
      this.setData({ selectedValue: selectedValue, "user.leftCount": "" });
    }
  },
  onShow: function () {
    const openId = wx.getStorageSync("openId");
    const token = wx.getStorageSync("token");
    const todayStr = getTodayStr();
    this.setData({
      selectedValue: this.data.items[0].value,
      openId: openId,
      token: token,
      "user.chargeAt": todayStr
    });
  },
  onNameChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.name": value, });
  },
  onChargeAtChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.chargeAt": value, });
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

  onMemoChange: function (e: any) {
    const value = e.detail.value;
    if (value !== null && value.length > 0) this.setData({ "user.memo": value, })
  },

  async confirm() {
    try {
      const data:any = {};
      data.phone = this.data.user.phone;
      if(!data.phone) {
        wx.showToast({ title: '请检查会员号!', duration: 1000, icon: 'error' });
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
      data.memo = this.data.user.memo;
      data.chargeAt = this.data.user.chargeAt;
      const resAddVip = await addVip("/consumers", this.data.openId, this.data.token, data);
      if (resAddVip.status != "ok") {
        wx.showToast({ title: '请检查会员号!', duration: 1000, icon: 'error' });
        return;
      }
      wx.navigateTo({"url": "/pages/alert-page/alert-page?res=success&msg=添加成功！&from=add-vip"});
    } catch (e) {
      wx.navigateTo({"url": "/pages/alert-page/alert-page?res=fail&msg=请检查会员号!&from=add-vip"});
      return;
    }
  },
  reset: function () { 
    this.setData({ "user.name": "", "user.phone": "", "user.money": "", "user.leftCount": "", "user.expiredAt": "", selectedValue: this.data.items[0].value});
  },
})