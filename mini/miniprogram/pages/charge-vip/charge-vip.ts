// pages/charge-vip/charge-vip.ts
import { app } from '../../app'; 
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
      round: "",
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onRadioChange: function(e: any) {
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