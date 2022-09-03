package com.puzzle.industries.domain.repository

import com.puzzle.industries.domain.common.crud.*

interface BaseHistoryRepository<T> : Insert<T>, Read<T>, Delete<T>