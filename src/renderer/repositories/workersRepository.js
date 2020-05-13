export default class WorkersRepository {
  constructor(dao) {
    this.dao = dao
  }
  /**
   * 建表
   */
  createTable() {
    const sql = `
          CREATE TABLE IF NOT EXISTS worker (
              id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
              cardtype int(2) DEFAULT ('1'),
              cardnumber varchar(255) NOT NULL,
              name varchar(255) NOT NULL,
              photo longtext DEFAULT NULL,
              livepic longtext DEFAULT NULL,
              nation int(10) DEFAULT NULL,
              haddress varchar(255) DEFAULT NULL,
              birthday varchar(255) DEFAULT NULL,
              gender int(6) DEFAULT NULL,
              office varchar(255) DEFAULT NULL,
              cbegin varchar(255) DEFAULT NULL,
              cend varchar(255) DEFAULT NULL,
              teamSysNo int(6) DEFAULT NULL,
              teamName varchar(255) DEFAULT NULL,
              workTypeCode int(20) DEFAULT NULL,
              workTypeName varchar(255) DEFAULT NULL,
              projectCode varchar(255) DEFAULT NULL,
              pwId varchar(255) DEFAULT NULL
            )
          `
    return this.dao.run(sql)
  }
  /**
   * 插入工人数据
   * @param {*} value k-v数据对象
   */
  create(value) {
    var statlist = []
    var vallist = []
    for (var i in value) {
      statlist.push(i)
      vallist.push(value[i])
    }
    var stat =
      `INSERT INTO worker(${statlist.join(',')}) VALUES (${new Array(statlist.length).fill('?').join(',')})`
    return this.dao.run(stat,
      vallist)
  }
  /**
   * 更新工人数据
   * @param {*} value 更新的k-v数据对象
   */
  update(value) {
    var statlist = []
    var vallist = []
    for (var i in value) {
      statlist.push(i)
      vallist.push(value[i])
    }

    var stat =
      `UPDATE worker SET ${statlist.join(' = ?, ')} = ? WHERE cardnumber = '${value.cardnumber}' AND projectCode = '${value.projectCode}'`
    return this.dao.run(stat,
      vallist)
  }
  updateLivePic(value) {
    var stat =
      `UPDATE worker SET livepic = ? WHERE cardnumber = ? AND projectCode = ?`
    return this.dao.run(stat,
      [value.livepic, value.cardnumber, value.projectCode])
  }
  /**
   * 根据用户身份证号查询人数，用于更新或插入验证
   * @param {*} userid 用户身份证号
   */
  getCount(userid, projectCode) {
    return this.dao.get(
      `SELECT count(id) as num FROM worker WHERE cardnumber = '${userid}' and projectCode = '${projectCode}'`)
  }
  /**
   * 根据关键词查询工人数量
   * @param {*} keyword 关键词
   */
  getWorkersCount(keyword, projectCode) {

    return this.dao.get(
      `SELECT count(id) as num from worker where (name like '%${keyword}%' or cardnumber like '%${keyword}%') and projectCode = ?`, [projectCode])
  }
  /**
   * 根据关键词和页码等查询工人数据
   * @param {*} keyword 关键词
   * @param {*} pagenum 页码
   * @param {*} pagesize 页大小
   */
  getWorkers(keyword, pagenum, pagesize, projectCode) {

    return this.dao.all(
      `SELECT * from worker where (name like '%${keyword}%' or cardnumber like '%${keyword}%') and projectCode = ? limit ?,?`,
      [projectCode, (pagenum - 1) * pagesize, pagesize])
  }

  getPostWorkers() {
    return this.dao.all(
      `SELECT * from worker`)
  }

  delById(pwid) {
    var stat =
      `DELETE FROM worker where id = ?`
    return this.dao.run(stat,
      [pwid])
  }

}