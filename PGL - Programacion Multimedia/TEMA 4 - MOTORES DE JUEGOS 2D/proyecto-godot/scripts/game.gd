extends Node

var points : int = 0
var lifes: int = 3

# Own signals to notify changes in UI
signal changed_points(new_points: int)
signal changed_lifes(new_lifes: int)

func add_points(quantity: int) -> void:
	points += quantity
	changed_points.emit(points)

func lose_life() -> void:
	lifes -= 1
	changed_lifes.emit(lifes)
	if lifes <= 0:
		get_tree().change_scene_to_file("res://game_over.tscn")
	else:
		get_tree().reload_current_scene()

func restart_game() -> void:
	points = 0
	lifes = 3
	get_tree().change_scene_to_file("res://main.tscn")
