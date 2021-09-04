package model

class Department() {
    var dno: Int = 0
    var dname: String = ""
    var dmanager: String = ""
    fun getDepname(): String {
        return dname
    }
    fun getDepno(): Int {
        return dno
    }
    fun getDepmanager(): String {
        return dmanager
    }
    fun setDepname(depname: String) {
        this.dname = depname
    }
    fun setDepno(dnum: Int) {
        this.dno = dnum
    }
    fun setDepmanager(depManager: String) {
        this.dmanager = depManager
    }
}
