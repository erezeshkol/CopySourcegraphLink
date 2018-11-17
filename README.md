# CopySourcegraphLink

Copy Sourcegraph Link is an IntelliJ plugin that lets you create a Sourcegraph link that points to the current line.

## Installation

- In your JetBrains IDE, go to `Preferences` (or `Settings` on Windows)
- Select `Plugins` in the left-hand pane
- Click `Browse repositories...`
- Search for `Copy Sourcegraph Link` and click `Install`

## Configuration

- In your JetBrains IDE, go to `Preferences` (or `Settings` on Windows)
- Select `Tools` in the left-hand pane > Copy Sourcegraph Link
- Set the `Server Path` to point to your sourcegraph server
- Configure your `Development Folder` (see next line for explanation)

At the moment, the plugin requires you to configure your development directory, and it depends on your directory tree having the same structure as the paths on Sourcegraph.  
For example, if your Sourcegraph server is configured to show repo_a under sourcegraph.mycompany.com/repo_a and repo_b under sourcegraph.mycompany.com/services/repo_b, your local directory tree should be:  
`.../<dev dir>/repo_a`  
`.../<dev dir>/services/repo_b`  

This requirement is due to the fact that there's no way to configure a mapping between a git remote URL and the Sourcegraph path. This will be solved in the future.

## Reporting Issues

Does the plugin not work for you as expected, even after configuring your setup according to the instructions above?  
Please report a new issue! In order to help me figure out what's going on, please make sure to include:  
1. What your git remote is (the output of `git remote -v`)
2. Which URL you're getting from the plugin (in case you got one, or a stacktrace if the plugin just raised an error)
3. Which URL you're expecting to get
