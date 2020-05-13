const {
    db
  } = require('electron').remote.require('./persistence')
  
  db.defaults({
    userlog: {
      mode: '1',
      username: '',//whlz05
      password: ''
    }
  }).write()
  
  export default {
    getUserlog () {
      return db.get('userlog')
        .cloneDeep()
        .value()
    },
    updateUserlog (updateProp) {
      return db.get('userlog')
        .assign(updateProp)
        .write()
    }
  }
  