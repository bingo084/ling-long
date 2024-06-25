package com.bingo.linglong.system.service

import cn.dev33.satoken.annotation.SaCheckPermission
import com.bingo.linglong.system.entity.User
import com.bingo.linglong.system.entity.by
import com.bingo.linglong.system.entity.copy
import com.bingo.linglong.system.entity.dto.UserInput
import com.bingo.linglong.system.repository.UserRepository
import org.babyfish.jimmer.Page
import org.babyfish.jimmer.client.FetchBy
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/system/users")
class UserService(val repository: UserRepository) {
    /**
     * 分页查询
     */
    @GetMapping
    @SaCheckPermission("system:user:list")
    fun findPage(
        @RequestParam index: Int,
        @RequestParam size: Int,
    ): Page<@FetchBy("COMPLEX_USER") User> =
        repository.findPage(index, size, COMPLEX_USER)

    /**
     * 保存
     */
    @PostMapping
    @SaCheckPermission("system:user:save")
    fun save(@RequestBody input: UserInput): Long =
        repository.save(input.toEntity().copy { password = "123456" }).id

    /**
     * 删除
     */
    @DeleteMapping("/{ids}")
    @SaCheckPermission("system:user:delete")
    fun deleteById(@PathVariable ids: List<Long>) = repository.deleteByIds(ids)

    companion object {
        val COMPLEX_USER = newFetcher(User::class).by {
            allScalarFields()
            dept { allScalarFields() }
            roles {
                allScalarFields()
                menus { allScalarFields() }
            }
            permissions()
            creator {
                username()
                nickname()
                realName()
            }
            updater {
                username()
                nickname()
                realName()
            }
        }
    }
}
