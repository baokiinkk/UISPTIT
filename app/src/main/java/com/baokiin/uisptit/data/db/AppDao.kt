package com.baokiin.uisptit.data.db

import androidx.room.*
import com.baokiin.uis.data.db.model.SemesterMark
import com.baokiin.uisptit.data.db.model.Mark

@Dao
interface AppDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addMark(mark: Mark)
//
    @Insert(onConflict = OnConflictStrategy.IGNORE) // khi trung thi bo qua
    suspend fun addSemester(semester: SemesterMark)

//    // delete
    @Delete
    suspend fun deleteMark(vararg mark: Mark)
//

//    //query
    @Query("select * from Mark Where Mark.semester=:hocki")
    suspend fun getMarkHK(hocki:String): MutableList<Mark>
    @Query("select * from Mark ")
    suspend fun getMark(): MutableList<Mark>
    @Query("DELETE  from Mark")
    suspend fun deleteMark()
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