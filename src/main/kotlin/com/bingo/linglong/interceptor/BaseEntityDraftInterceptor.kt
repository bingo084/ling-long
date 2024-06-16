package com.bingo.linglong.interceptor

import cn.dev33.satoken.stp.StpUtil
import com.bingo.linglong.common.BaseEntity
import com.bingo.linglong.common.BaseEntityDraft
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BaseEntityDraftInterceptor(
) : DraftInterceptor<BaseEntity, BaseEntityDraft> {

    override fun beforeSave(draft: BaseEntityDraft, original: BaseEntity?) {
        if (!isLoaded(draft, BaseEntity::updateTime)) {
            draft.updateTime = LocalDateTime.now()
        }

        if (!isLoaded(draft, BaseEntity::updater)) {
            draft.updater {
                id = StpUtil.getLoginIdAsLong();
            }
        }

        if (original === null) {
            if (!isLoaded(draft, BaseEntity::createTime)) {
                draft.createTime = LocalDateTime.now()
            }

            if (!isLoaded(draft, BaseEntity::creator)) {
                draft.creator {
                    id = StpUtil.getLoginIdAsLong();
                }
            }
        }
    }
}