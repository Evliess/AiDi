// index.ts

const app = getApp()
Page({
  data: {
    safeTop: 0,
    mems:[{"name":"zhangsan", "time":"2025-01-01"},{"name":"lisi", "time":"2025-01-21"}]
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
