// pages/add-vip/add-vip.ts
let app = getApp()
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
      type: "",
      round: "",
    }
  },
  onRadioChange: function(e) {
    const selectedValue = e.detail.value;
    this.setData({
      selectedValue: selectedValue
    });
    console.log('选中的值变为:', selectedValue);
  },
  onLoad: function() {
    const safeTop = app.globalData.safeTop;
    this.setData({
      safeTop: safeTop,
      selectedValue: this.data.items[0].value
    });
  },
})