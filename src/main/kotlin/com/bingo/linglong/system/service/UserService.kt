package com.bingo.linglong.system.service

import com.bingo.linglong.system.entity.User
import com.bingo.linglong.system.entity.dto.UserInput
import com.bingo.linglong.system.repository.UserRepository
import org.babyfish.jimmer.Page
import org.babyfish.jimmer.sql.fetcher.Fetcher
import org.springframework.stereotype.Service

@Service
class UserService(val repository: UserRepository) {
    fun findPage(current: Int, size: Int, fetcher: Fetcher<User>): Page<User> =
        repository.findPage(current, size, fetcher)

    fun save(input: UserInput): User = repository.save(input)

    fun deleteById(id: Long) = repository.deleteById(id)
}
