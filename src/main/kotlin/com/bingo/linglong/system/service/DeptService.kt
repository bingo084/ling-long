package com.bingo.linglong.system.service

import com.bingo.linglong.system.entity.Dept
import com.bingo.linglong.system.entity.by
import com.bingo.linglong.system.entity.dto.DeptInput
import com.bingo.linglong.system.repository.DeptRepository
import org.babyfish.jimmer.client.FetchBy
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/system/depts")
class DeptService(val repository: DeptRepository) {
    /**
     * 查询树
     */
    @GetMapping
    fun findTree(): List<@FetchBy("TREE_DEPT") Dept> =
        repository.findRoot(TREE_DEPT)

    /**
     * 保存
     */
    @PostMapping
    fun save(dept: DeptInput): Long = repository.save(dept).id

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    fun deleteById(@PathVariable ids: List<Long>) = repository.deleteByIds(ids)

    companion object {
        val TREE_DEPT = newFetcher(Dept::class).by {
            allScalarFields()
            `children*`()
        }
    }
}