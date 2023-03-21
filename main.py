import pygame
import math
import random
from pygame import mixer


running_whole = True
pygame.init()


while running_whole:
    pygame.display.set_caption("Flappy Bird")

    pygame.init()


    DISPLAY_X = 600
    DISPLAY_Y = 500
    FPS = 60
    SCREEN = pygame.display.set_mode((DISPLAY_X, DISPLAY_Y))
    SCROLL_SPEED = 2.5


    BIRD_SIZE_X = 80
    BIRD_SIZE_Y = 40.75
    BIRD_POS_X = 100  # Constant bird x position
    bird_pos_y = 200  # Initial bird y position

    SCORE_FONT = pygame.font.Font('freesansbold.ttf', 64)
    GAME_OVER_FONT = pygame.font.Font('freesansbold.ttf', 128)

    game_over = False

    PIPE_X_START = 600  # Rightmost starting point of pipe
    PIPE_DIFFERENCE = 450  # Distance between upper pipe and down pipe

    BACKGROUND = pygame.image.load("FlappyBird Assets/flappy_background.png")
    BIRD = pygame.image.load("FlappyBird Assets/the_bird.png")
    BIRD_ICON = pygame.transform.scale(BIRD, (64, 64))
    BIRD = pygame.transform.scale(BIRD, (BIRD_SIZE_X, BIRD_SIZE_Y))

    pygame.display.set_icon(BIRD_ICON)

    CLOCK = pygame.time.Clock()
    CLOCK.tick(FPS)

    background_X = 0
    running = True
    space_to_start = True
    ceiling = -1  # Initialize ceiling
    ascending = False  # Bird is not ascending; initialize ascending boolean variable
    final_pipe = 0

    score = 0


    def display_score(x, y):
        score_rendered = SCORE_FONT.render(str(score), True, (255, 255, 255))
        SCREEN.blit(score_rendered, (x, y))

    # Displays background
    def display_background(background, background_X):
        if background_X % SCREEN.get_width() == 0:
            background_X = 0
        SCREEN.blit(background, (background_X, 0))  # Blit the background (x variable is decremented)
        SCREEN.blit(background, (
            background_X % SCREEN.get_width(), 0))  # Blit background on the remainder of screen; creates scroll effect
        background_X -= SCROLL_SPEED
        return background_X




    # Moves bird downward, unless ascending is true
    def bird_movement(ascending, bird_pos_y, ceiling):
        # Bird ascends until the ceiling is hit
        if ascending:
            if bird_pos_y <= ceiling:
                ascending = False
            else:
                bird_pos_y -= 13
        else:
            bird_pos_y += 2.5

        return ascending, bird_pos_y


    class Pipe:
        def __init__(self, pipe_x, pipe_difference):
            self.PIPE_UP = pygame.image.load("FlappyBird Assets/pipe_up.png")
            self.PIPE_WIDTH = 60
            self.PIPE_HEIGHT = 320
            self.PIPE_UP = pygame.transform.scale(self.PIPE_UP, (self.PIPE_WIDTH, self.PIPE_HEIGHT))
            self.PIPE_DOWN = pygame.transform.rotate(self.PIPE_UP, 180)
            self.pipe_x = pipe_x
            self.pipe_down_y = 0
            self.pipe_up_y = 0
            self.PIPE_DIFFERENCE = pipe_difference
            self.accounted_for = False
            self.set_new_pipe_dimensions()

        def display_pipe(self, PIPE_X_START, game_over):
            if self.pipe_x >= -70:
                SCREEN.blit(self.PIPE_DOWN, (self.pipe_x, self.pipe_down_y))
                SCREEN.blit(self.PIPE_UP, (self.pipe_x, self.pipe_up_y))

                if not game_over:
                    self.pipe_x -= SCROLL_SPEED

            else:
                self.pipe_x = PIPE_X_START  # sets pipe x back to just after the right side of the screen
                self.set_new_pipe_dimensions()  # sets new pipe dimensions

        def get_pipe_up_y(self, pipe_down_y):
            return pipe_down_y + self.PIPE_DIFFERENCE  # returns lower pipe y coordinate, with the space in between defined by PIPE_DIFFERENCE

        # Creates new pipe
        def set_new_pipe_dimensions(self):
            self.pipe_down_y = random.randint(-250, 0)  # Randomizes pipe dimensions
            self.pipe_up_y = self.get_pipe_up_y(self.pipe_down_y)
            self.accounted_for = False


    pipe1 = Pipe(PIPE_X_START, PIPE_DIFFERENCE)  # Creates pipe starting at just after right most side of screen
    # Creates pipe starting even further after the right most side of screen to act as "2nd" pipe
    # Note that the 2nd pipes 2nd iteration puts its x coordinate back to 0, making it into a "normal" pipe
    pipe2 = Pipe(PIPE_X_START + 350, PIPE_DIFFERENCE)

    # Game Loop
    while running:
        CLOCK.tick(FPS)
        SCREEN.fill((0, 0, 0))

        # Display background
        if game_over:
            not_background = display_background(BACKGROUND, background_X)
        else:
            background_X = display_background(BACKGROUND, background_X)

            ascending, bird_pos_y = bird_movement(ascending, bird_pos_y, ceiling)

            # Handles bird hitting bottom of screen
            if bird_pos_y >= DISPLAY_Y - BIRD_SIZE_Y:
                bird_pos_y = DISPLAY_Y - BIRD_SIZE_Y
                mixer.Sound("FlappyBird Assets/die.wav").play()
                game_over = True

            # Handles collision with pipe
            pipes = [pipe1, pipe2]
            for pipe in pipes:
                # if the x position of the bird is within the pipe
                if BIRD_POS_X - 35 <= pipe.pipe_x <= BIRD_POS_X + pipe.PIPE_WIDTH:
                    # if the birds y position is less than (further up) the down pipe or greater than (further down) the up pipe
                    if bird_pos_y <= pipe.pipe_down_y + pipe.PIPE_HEIGHT - 10 or bird_pos_y >= pipe.pipe_up_y - 35:
                        mixer.Sound("FlappyBird Assets/die.wav").play()
                        game_over = True
                if pipe.pipe_x + pipe.PIPE_WIDTH - 35 < BIRD_POS_X and not pipe.accounted_for:
                    score += 1
                    pipe.accounted_for = True
                    mixer.Sound("FlappyBird Assets/point.wav").play()


        # Displays both pipes (PIPE_X_START is the coordinate the pipe returns to after an iteration
        pipe1.display_pipe(PIPE_X_START, game_over)
        pipe2.display_pipe(PIPE_X_START, game_over)
        SCREEN.blit(BIRD, (BIRD_POS_X, bird_pos_y))

        display_score(280, 50)



        # Event handling
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
                running_whole = False
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE:
                    if game_over:
                        game_over = True
                        running = False
                    ceiling = bird_pos_y - 70  # sets ceiling when space is pressed
                    if ceiling < 0:
                        ceiling = 10
                    ascending = True  # bird ascends when space is pressed until ceiling is hit
                    mixer.Sound("FlappyBird Assets/wing.wav").play()

        # Display GAME OVER overlay
        if game_over:
            game_over_rendered = SCORE_FONT.render("GAME OVER", True, (255, 255, 255))
            SCREEN.blit(game_over_rendered, (100, 150))
            
        pygame.display.update()

    pygame.quit()
