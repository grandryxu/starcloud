import {
  app,
  BrowserWindow
} from 'electron'

/**
 * Set `__static` path to static files in production
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-static-assets.html
 */
if (process.env.NODE_ENV !== 'development') {
  global.__static = require('path').join(__dirname, '/static').replace(/\\/g, '\\\\')
}

let mainWindow
const winURL = process.env.NODE_ENV === 'development' ?
  `http://localhost:9080` :
  `file://${__dirname}/index.html`

const path = require('path')
global.userDataPath = path.join('resources/settings.json')

function createWindow() {
  /**
   * Initial window options
   */
  mainWindow = new BrowserWindow({
    height: 650,
    useContentSize: true,
    width: 1000,
    resizable: false,
    frame: false,
    webPreferences: {
      webSecurity: false,
      defaultFontSize: 14
    },
    show: false,
    backgroundColor: '#f8f8f9'
  })

  mainWindow.loadURL(winURL)
  mainWindow.setMenu(null)
  //mainWindow.webContents.openDevTools()
  mainWindow.once('ready-to-show', () => {
    mainWindow.show()
  })
  mainWindow.on('closed', () => {
    mainWindow = null
  })
}

app.on('ready', createWindow)

app.on('window-all-closed', () => {
  app.quit()
})


if (process.env.NODE_ENV === 'development')
  require('electron-context-menu')({
    showInspectElement: true,
    labels: {
      cut: '剪切',
      copy: '复制',
      paste: '粘贴',
      save: '保存',
      saveImageAs: '图像存储为...',
      copyLink: '复制链接',
      copyImageAddress: '复制图像地址',
      inspect: '打开调试工具'
    }
  })