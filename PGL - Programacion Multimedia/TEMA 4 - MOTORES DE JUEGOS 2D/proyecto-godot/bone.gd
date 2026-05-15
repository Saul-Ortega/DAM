extends Area2D

@export var value : int = 10
@onready var boneSound = $BoneSound

func _ready():
	body_entered.connect(_on_taken)

func _on_taken(body: Node2D):
	if body.is_in_group("player"):
		Game.add_points(value)
		boneSound.play()
		$CollisionShape2D.set_deferred("disabled", true)
		#		await get_tree().create_timer(0.3).timeout
		await get_tree().create_timer(0.3).timeout
		queue_free()
