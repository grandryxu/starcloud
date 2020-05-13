const path = require('path')

/**
 * `electron-packager` options
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-electron-packager.html
 */
module.exports = {
  arch: process.env.ARCH || 'all',
  asar: true,
  dir: path.join(__dirname, '../'),
  icon: path.join(__dirname, '../build/icons/icon'),
  ignore: /(^\/(src|test|\.[a-z]+|README|yarn|static|dist\/web))|\.gitkeep/,
  extraResource: ['resources/IDCardLib.dll',
    'resources/IDCardLib.pdb',
    'resources/termb.dll',
    'resources/Newtonsoft.Json.dll',
    'resources/Newtonsoft.Json.xml',
    'resources/Newtonsoft.Json.pdb',
    'resources/tiny_face_detector_model-shard1',
    'resources/tiny_face_detector_model-weights_manifest.json',
    'resources/i.jpg'
  ],
  electronVersion: '2.0.4',
  buildVersion: '0.0.1',
  overwrite: true,
  out: path.join(__dirname, '../build'),
  platform: process.env.BUILD_TARGET || 'all'
}