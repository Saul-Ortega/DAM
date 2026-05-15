extends CanvasLayer

@onready var points_label = $HBoxContainer/LabelPoints
@onready var lifes_label = $HBoxContainer/LabelLifes

func _ready():
	Game.changed_points.connect(_on_changed_points)
	Game.changed_lifes.connect(_on_changed_lifes)
	_on_changed_points(Game.points)
	_on_changed_lifes(Game.lifes)
	
func _on_changed_points(new_points: int):
	points_label.text = "Collected Bones: " + str(new_points)
	
func _on_changed_lifes(new_lifes: int):
	var hearts = ""
	for i in range(new_lifes):
		hearts += "❤️ "
	lifes_label.text = hearts
