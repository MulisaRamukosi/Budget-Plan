package com.puzzle.industries.domain.repository

import com.puzzle.industries.domain.common.Create
import com.puzzle.industries.domain.common.Delete
import com.puzzle.industries.domain.common.MultiDelete
import com.puzzle.industries.domain.common.ReadAll

interface BaseHistoryRepository<T> : Create<T>, ReadAll<List<T>>, Delete<T>, MultiDelete<T>