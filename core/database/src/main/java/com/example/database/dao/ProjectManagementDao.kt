package com.example.database.dao

import androidx.room.Dao
import com.example.database.dao.project_management.Project
import com.example.database.dao.project_management.Templates
import com.example.database.dao.project_management.TemplatesStatus


@Dao
interface ProjectManagementDao: Templates,Project, TemplatesStatus

