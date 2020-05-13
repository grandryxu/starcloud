export default class IndividualRepository {
  constructor(dao) {
    this.dao = dao
  }
  /**
   * 建表
   */
  createTable() {
    const sql = `
          CREATE TABLE IF NOT EXISTS individual (
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
              projectCode varchar(255) DEFAULT NULL,
              remoteId int(10) DEFAULT NULL
            )
          `
    return this.dao.run(sql)
  }

  create(value) {
    var statlist = []
    var vallist = []
    for (var i in value) {
      statlist.push(i)
      vallist.push(value[i])
    }
    var stat =
      `INSERT INTO individual(${statlist.join(',')}) VALUES (${new Array(statlist.length).fill('?').join(',')})`
    return this.dao.run(stat,
      vallist)
  }

  update(value) {
    var statlist = []
    var vallist = []
    for (var i in value) {
      statlist.push(i)
      vallist.push(value[i])
    }

    var stat =
      `UPDATE individual SET ${statlist.join(' = ?, ')} = ? 
        WHERE cardnumber = '${value.cardnumber}' 
        AND projectCode = '${value.projectCode}'`
    return this.dao.run(stat,
      vallist)
  }

  updateLivePic (value) {
    var stat =
      `UPDATE individual SET livepic = ? WHERE cardnumber = ? AND projectCode = ?`
    return this.dao.run(stat,
      [value.livepic,value.cardnumber,value.projectCode])
  }

  getLimitCount(userid, projectCode) {
    return this.dao.get(
      "SELECT count(id) as num FROM individual WHERE cardnumber = ? and projectCode = ?",
      [userid, projectCode])
  }

  getCount(keyword, projectCode) {
    return this.dao.get(
      `SELECT count(id) as num from individual 
        where (name like '%${keyword}%' or cardnumber like '%${keyword}%') 
        and projectCode = ?`, [projectCode])
  }

  getList(keyword, pagenum, pagesize, projectCode) {
    return this.dao.all(
      `SELECT * from individual where (name like '%${keyword}%' or cardnumber like '%${keyword}%') 
        and projectCode = ? limit ?,?`,
      [projectCode, (pagenum - 1) * pagesize, pagesize])
  }

  getPostList() {
    return this.dao.all(
      `SELECT * from individual`)
  }

  delById(id){
    var stat =
      `DELETE FROM individual where id = ?`
    return this.dao.run(stat,
      [id])
  }

}