// pages/non-vip/non-vip.ts
const app = getApp()
Page({
  data: {
    safeTop: 0,
  },
  methods: {
    bindViewTap() {
      console.log(123);
    },
  },
  onLoad: function() {
    const safeTop = app.globalData.safeTop;
    this.setData({
      safeTop: safeTop
    });
  },
})
