package com.bingo.linglong.system.service

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
    fun findPage(
        @RequestParam index: Int,
        @RequestParam size: Int,
    ): Page<@FetchBy("COMPLEX_USER") User> =
        repository.findPage(index, size, COMPLEX_USER)

    /**
     * 保存
     */
    @PostMapping
    fun save(input: UserInput): Long =
        repository.save(input.toEntity().copy { password = "123456" }).id

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long) = repository.deleteById(id)

    companion object {
        val COMPLEX_USER = newFetcher(User::class).by {
            allScalarFields()
            dept { allScalarFields() }
            roles { allScalarFields() }
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
