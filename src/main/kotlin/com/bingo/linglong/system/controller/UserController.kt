package com.bingo.linglong.system.controller

import com.bingo.linglong.common.R
import com.bingo.linglong.system.entity.User
import com.bingo.linglong.system.entity.by
import com.bingo.linglong.system.entity.dto.UserInput
import com.bingo.linglong.system.service.UserService
import org.babyfish.jimmer.Page
import org.babyfish.jimmer.client.FetchBy
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.web.bind.annotation.*

/**
 * 用户
 *
 * @author bingo
 */
@RestController
@RequestMapping("/system/users")
class UserController(val service: UserService) {
    /**
     * 分页查询
     */
    @GetMapping
    fun findPage(
        @RequestParam index: Int,
        @RequestParam size: Int
    ): R<Page<@FetchBy("COMPLEX_USER") User>> =
        R.ok("查询成功", service.findPage(index, size, COMPLEX_USER))

    /**
     * 保存
     */
    @PostMapping
    fun save(@RequestBody input: UserInput): R<User> = R.ok("保存成功", service.save(input))

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): R<User> {
        service.deleteById(id)
        return R.ok("删除成功", null)
    }

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