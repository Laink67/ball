package ru.laink.ball.other

class Constant {

    companion object {
        //Игровые константы
        const val MISS_PENALTY = 2 // Штраф при промахе
        const val HIT_REWARD = 3 // Прибавка при попадании

        // Констаныт для рисования пушки
        const val CANNON_BASE_RADIUS_PERCENT = 3.0 / 40
        const val CANNON_BARREL_WIDTH_PERCENT = 1.0 / 20/*40*/
        const val CANNON_BARREL_LENGTH_PERCENT = 1.0 / 12

        // Константы для рисования ядра
        const val CANNONBALL_RADIUS_PERCENT = 3.0 / 80
        const val CANNONBALL_SPEED_PERCENT = 3.0 / 2

        // Константы для рисования мишени
        const val TARGET_WIDTH_PERCENT = 1.0 / 40
        const val TARGET_LENGTH_PERCENT = 3.0 / 20
        const val TARGET_FIRST_X_PERCENT = 3.0 / 5
        const val TARGET_SPACING_PERCENT = 1.0 / 60
        const val TARGET_PIECES = 4
        const val TARGET_MIN_SPEED_PERCENT = 1.0 / 8
        const val TARGET_MAX_SPEED_PERCENT = 5.0 / 4

        // Размер текста составляет 1/18 ширины экрана
        const val TEXT_SIZE_PERCENT = 1.0 / 18

        // Для управления звуком
        const val TARGET_SOUND_ID = 0
        const val CANNON_SOUND_ID = 1

        // Погрешность
        const val ERROR = 15

        const val ARRIVAL_ZONE_VERT_SPACE = 200
        const val START_ZONE_VERT_SPACE = 200
        const val VERTICAL_GAP = 300
        const val HORIZONTAL_GAP = 100
        const val PLATFORM_HEIGHT = 20
        const val MAX_VERTICAL_WALLS = 3
        val WALL_WIDTH: Int =
            PLATFORM_HEIGHT
        val WALL_HEIGHT: Int = VERTICAL_GAP - PLATFORM_HEIGHT - 30
    }

}