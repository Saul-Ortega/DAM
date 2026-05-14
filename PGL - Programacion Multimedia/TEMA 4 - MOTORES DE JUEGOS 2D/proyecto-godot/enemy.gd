extends CharacterBody2D

# WATCHER PARAMETERS
@export var watcher_velocity : float = 80.0
@export var watcher_distance : float = 45.0

# INTERNAL VARIABLES
var current_direction : float = 1.0
var initial_positioin : float

var gravity = ProjectSettings.get_setting("physics/2d/default_gravity")

@onready var sprite = $AnimatedSprite2D

func _ready():
	initial_positioin = global_position.x
	
func _physics_process(delta):
	if not is_on_floor():
		velocity.y += gravity * delta
		
	velocity.x = watcher_velocity * current_direction
	
	var distance_traveled = global_position.x - initial_positioin
	
	if distance_traveled >= watcher_distance:
		current_direction = -1.0
	elif distance_traveled <= -watcher_distance:
		current_direction = 1.0
	
	sprite.flip_h = (current_direction == -1.0)
	
	_update_animation(current_direction)
	
	move_and_slide()


func _on_area_2d_body_entered(body: Node2D) -> void:
	if body.is_in_group("player"):
		Game.lose_life()
		

func _update_animation(direction: int):
	if direction != 0:
		sprite.flip_h = (direction == -1)
		
	if direction != 0:
		sprite.play("walking")
	else:
		sprite.play("idle")
