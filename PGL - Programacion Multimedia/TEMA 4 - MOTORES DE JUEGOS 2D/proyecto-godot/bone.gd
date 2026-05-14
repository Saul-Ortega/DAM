extends Area2D

@export var value : int = 10

func _ready():
	body_entered.connect(_on_taken)

func _on_taken(body: Node2D):
	if body.is_in_group("player"):
		Game.add_points(value)
	
	queue_free()
