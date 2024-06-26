package com.bingo.linglong.system.service

import cn.dev33.satoken.annotation.SaCheckPermission
import com.bingo.linglong.system.entity.Menu
import com.bingo.linglong.system.entity.by
import com.bingo.linglong.system.entity.dto.MenuInput
import com.bingo.linglong.system.repository.MenuRepository
import org.babyfish.jimmer.client.FetchBy
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/system/menus")
class MenuService(val repository: MenuRepository) {
    /**
     * 查询树
     */
    @GetMapping
    @SaCheckPermission("system:menu:list")
    fun findTree(): List<@FetchBy("TREE_MENU") Menu> =
        repository.findRoot(TREE_MENU)

    /**
     * 保存
     */
    @PostMapping
    @SaCheckPermission("system:menu:save")
    fun save(@RequestBody menu: MenuInput): Long = repository.save(menu).id

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    @SaCheckPermission("system:menu:delete")
    fun deleteById(@PathVariable ids: List<Long>) = repository.deleteByIds(ids)

    companion object {
        val TREE_MENU = newFetcher(Menu::class).by {
            allScalarFields()
            `children*`()
        }
    }
}