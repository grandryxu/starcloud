export default class TeamRepository {
  constructor(dao) {
    this.dao = dao
  }

  createTable() {
    const sql = `
            CREATE TABLE IF NOT EXISTS team (
                id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
                teamName varchar(255) NOT NULL,
                projectCode varchar(255) NOT NULL,
                teamLeader varchar(255) DEFAULT NULL,
                teamLeaderIdNumber varchar(255) DEFAULT NULL,
                teamPhoneCall varchar(255) DEFAULT NULL,
                teamCount INTEGER DEFAULT 0,
                remoteid INTEGER DEFAULT 0
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
      `INSERT INTO team(${statlist.join(',')}) VALUES (${new Array(statlist.length).fill('?').join(',')})`
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
      `UPDATE team SET ${statlist.join(' = ?, ')} = ? WHERE remoteid = '${value.remoteid}' AND projectCode = '${value.projectCode}'`
    return this.dao.run(stat,
      vallist)
  }

  getLimitCount(projectCode, remoteid) {
    return this.dao.get(
      `SELECT count(id) as num FROM team 
            WHERE projectCode = ? and remoteid = ?`,
      [projectCode, remoteid])
  }

  getCount(keyword, projectCode) {
    return this.dao.get(
      `SELECT count(id) as num from team 
          where teamName like '%${keyword}%'
          and projectCode = ?`, [projectCode])
  }

  getList(keyword, pagenum, pagesize, projectCode) {
    return this.dao.all(
      `SELECT * from team where teamName like '%${keyword}%'
          and projectCode = ? limit ?,?`,
      [projectCode, (pagenum - 1) * pagesize, pagesize])
  }

  drop(value) {
    var stat =
      `DELETE FROM team WHERE id = ${value}`
    return this.dao.run(stat)
  }

}