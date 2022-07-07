package com.ramiyon.soulmath.data.worker

const val workerCommand = "Worker Command"

enum class WorkerCommand(val command: String) {
    WORKER_COMMAND_UPDATE_PROFILE("UPDATE_PROFILE"),
    WORKER_COMMAND_UPDATE_XP("UPDATE_XP"),
}