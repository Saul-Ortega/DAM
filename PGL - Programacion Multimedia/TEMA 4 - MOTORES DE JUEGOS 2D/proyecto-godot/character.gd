extends CharacterBody2D

# Speed horizontal movement (pixels per second)
const SPEED = 300.0 # Pixels per second (horizontal)
const JUMP_POWER = 400.0 # Jump initial speed (up)

# We obtain the gravity of the project
var gravity = ProjectSettings.get_setting("physics/2d/default_gravity")

var coyote_timer = 0.0
const COYOTE_TIME = 0.1

@onready var sprite = $AnimatedSprite2D
@onready var deathSound = $DeathSound

func _physics_process(delta):
	if not is_on_floor():
		velocity.y += gravity * delta
	
	if Input.is_action_just_released("ui_accept") and velocity.y < 0:
		velocity.y *= 0.5
	
	if is_on_floor():
		coyote_timer = COYOTE_TIME
	else:
		coyote_timer -= delta
	
	if Input.is_action_just_pressed("ui_accept") and is_on_floor():
		velocity.y = -JUMP_POWER
		coyote_timer = 0
	
	# Read keyboard direction (-1 left, 0 stopped, +1 right)
	var direction = 0
	
	if Input.is_action_pressed('ui_right'):
		direction = 1
	elif Input.is_action_pressed('ui_left'):
		direction = -1
	
	# Assign horizontal speed
	velocity.x = direction * SPEED
	
	_update_animation(direction)
	
	# move_and_slide() moves the body and handles collisions
	move_and_slide()

func death_with_sound():
	deathSound.play()
	await deathSound.finished

func _update_animation(direction: int):
	if direction != 0:
		sprite.flip_h = (direction == -1)
		
	if not is_on_floor():
		sprite.play("jump")
	elif direction != 0:
		sprite.play("run")
	else:
		sprite.play("idle")
