package com.puzzle.industries.domain.repository

import com.puzzle.industries.domain.common.Create
import com.puzzle.industries.domain.common.Delete
import com.puzzle.industries.domain.common.Read

interface BaseHistoryRepository<T> : Create<T>, Read<List<T>>, Delete<T>