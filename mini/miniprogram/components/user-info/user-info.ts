// components/user-info/user-info.ts
Component({
  properties: {
    jsonData: {
      type: Object,
      value: {}
    }
  },
  data: {
    displayItems: []
  },
  observers: {
    'jsonData': function(newVal) {}
  }
})