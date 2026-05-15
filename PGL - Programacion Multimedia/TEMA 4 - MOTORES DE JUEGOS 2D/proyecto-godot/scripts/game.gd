extends Node

var points : int = 0
var lifes: int = 3

# Own signals to notify changes in UI
signal changed_points(new_points: int)
signal changed_lifes(new_lifes: int)

func add_points(quantity: int) -> void:
    points += quantity
    changed_points.emit(points)
    
    if points >= 50:
        win_game()

func lose_life() -> void:
    lifes -= 1
    points = 0
    changed_lifes.emit(lifes)
    changed_points.emit(points)
    
    if lifes <= 0:
        await get_tree().create_timer(0.5).timeout
        get_tree().change_scene_to_file("res://game_over.tscn")
    else:
        await get_tree().create_timer(0.5).timeout
        get_tree().reload_current_scene()

func win_game() -> void:
    get_tree().change_scene_to_file.call_deferred("res://game_win.tscn")

func restart_game() -> void:
    points = 0
    lifes = 3
    get_tree().change_scene_to_file("res://main.tscn")
