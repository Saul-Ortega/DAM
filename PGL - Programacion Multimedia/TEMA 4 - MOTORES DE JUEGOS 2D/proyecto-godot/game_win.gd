extends CanvasLayer

@onready var punctuation_label = $CenterContainer/VBoxContainer/PunctutationLabel
@onready var restart_button = $CenterContainer/VBoxContainer/Button

func _ready():
	punctuation_label.text = "Final Punctuation: " + str(Game.points)
	restart_button.pressed.connect(_on_restart)

func _on_restart():
	Game.restart_game()
