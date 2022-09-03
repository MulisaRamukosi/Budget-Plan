package com.puzzle.industries.domain.repository

import com.puzzle.industries.domain.common.crud.*

interface BaseHistoryRepository<T> : Create<T>, Read<T>, Delete<T>