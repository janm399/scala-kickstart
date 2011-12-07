module Functions where

import Random

nonPure :: IO Double
nonPure = randomIO