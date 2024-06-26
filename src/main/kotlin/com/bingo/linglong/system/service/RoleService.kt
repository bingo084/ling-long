package com.bingo.linglong.system.service

import cn.dev33.satoken.annotation.SaCheckPermission
import com.bingo.linglong.system.entity.Role
import com.bingo.linglong.system.entity.by
import com.bingo.linglong.system.entity.dto.RoleInput
import com.bingo.linglong.system.repository.RoleRepository
import org.babyfish.jimmer.Page
import org.babyfish.jimmer.client.FetchBy
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/system/roles")
class RoleService(val repository: RoleRepository) {
    /**
     * 分页查询
     */
    @GetMapping
    @SaCheckPermission("system:role:list")
    fun findPage(
        @RequestParam index: Int,
        @RequestParam size: Int
    ): Page<@FetchBy("SIMPLE_ROLE") Role> =
        repository.findPage(index, size, SIMPLE_ROLE)

    /**
     * 保存
     */
    @PostMapping
    @SaCheckPermission("system:role:save")
    fun save(@RequestBody role: RoleInput): Long = repository.save(role).id

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    @SaCheckPermission("system:role:delete")
    fun deleteById(@PathVariable ids: List<Long>) = repository.deleteByIds(ids)

    companion object {
        val SIMPLE_ROLE = newFetcher(Role::class).by {
            allScalarFields()
        }
    }
}