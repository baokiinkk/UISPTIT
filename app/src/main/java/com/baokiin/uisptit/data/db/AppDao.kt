package com.baokiin.uisptit.data.db

import androidx.room.*
import com.baokiin.uisptit.data.db.model.*

@Dao
interface AppDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addMark(mark: Mark)
//
    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addSemester(semester: SemesterMark)

    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addExamTimeTable(timetable: ExamTimetable)

    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addTimeTable(timetable: TimeTable)

    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addUser(login: LoginInfor)

    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addInforUser(login: InfoUser)


//    // delete
    @Delete
    suspend fun deleteMark(vararg mark: Mark)
    @Delete
    suspend fun deleteSemester(vararg semester: SemesterMark )
    @Delete
    suspend fun deleteExamTimeTable(vararg timetable: ExamTimetable )
    @Delete
    suspend fun deleteTimeTable(vararg timetable: TimeTable )
//

//    //query
    @Query("select * from Mark Where Mark.semester=:hocki")
    suspend fun getMarkHK(hocki:String): MutableList<Mark>
    @Query("select * from Mark ")
    suspend fun getMark(): MutableList<Mark>

    @Query("select * from SemesterMark ")
    suspend fun getSemester(): MutableList<SemesterMark>
    @Query("select * from SemesterMark Where SemesterMark.semester=:hocki")
    suspend fun getSemesterHK(hocki:String): MutableList<SemesterMark>

    @Query("select * from ExamTimetable ")
    suspend fun getExamTimeTable(): MutableList<ExamTimetable>

    @Query("select * from TimeTable ")
    suspend fun getTimeTable(): MutableList<TimeTable>
    @Query("select * from TimeTable Where TimeTable.tuan=:tuan")
    suspend fun getTimeTable(tuan:String): MutableList<TimeTable>

    @Query("DELETE  from LoginInfor")
    suspend fun deleteLogin()
    @Query("select * from LoginInfor")
    suspend fun getLogin():MutableList<LoginInfor>

    @Query("select * from InfoUser")
    suspend fun getInforUser():InfoUser

    @Query("DELETE  from Mark")
    suspend fun deleteMark()
    @Query("DELETE  from ExamTimetable")
    suspend fun deleteExam()
    @Query("DELETE  from SemesterMark")
    suspend fun deleteSemester()
    @Query("DELETE  from TimeTable")
    suspend fun deleteTimeTable()
    @Query("DELETE  from InfoUser")
    suspend fun deleteInforUser()
//
//    @Query("select * from Score")
//    suspend fun getScore(): Score
//    @Query("DELETE  from Score")
//    suspend fun deleteScore()
//
//    @Query("select * from DeThi Where DeThi.bomon= :bomon")
//    suspend fun getDethi(bomon:String): MutableList<DeThi>
//    @Query("DELETE  from DeThi")
//    suspend fun deleteDeThi()
//
//    @Query("select * from DeThi Where DeThi.ten=:bomon")
//    suspend fun getDethiS(bomon:String): DeThi
//
//    @Query("select * from BaiThi Where BaiThi.DeThiID=:idDeThi")
//    suspend fun getBaiThi(idDeThi: String): MutableList<BaiThi>
//    @Query("DELETE  from BaiThi")
//    suspend fun deleteBaiThi()

}