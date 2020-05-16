## Branching / CI Pipeline
Work is typically done on a `feature/*` or `bug/*` branch. Push to such branch does not trigger a CI pipeline. It is responsibility of a committer to ensure that branch is clean and ready to pass CI pipeline after merge. Custom branches are tested locally on the developer's workstation. They are eventually merged to `develop`.

* Direct commits to `develop` should be avoided. Exceptions could be version bumps, minor documentation updates, etc.

Merge to `develop` triggers CI pipeline which:

* runs a full test suite (unit/integration)
* builds latest **snapshot** docker image tagged as `:develop` and pushes it to [dockerhub](https://hub.docker.com/search?q=mrazjava%2Fbooklink&type=image) only
* sandbox `stg` will immediately use the new dockerhub image on next run

When staged changes are ready for general QA, merge from `develop` into `master` occurs.

* Direct commits to `master` should never happen except for hotfixes. If hotfix is applied to `master`, it is immediately backported to `develop`.

Merge to `master` triggers release CI pipeline which:

* builds application from `master` sources
* assembles latest **release** docker image tagged as `:master` and pushes it to [dockerhub](https://hub.docker.com/search?q=mrazjava%2Fbooklink&type=image) and Amazon ECR
* AWS `pre` release environment is automatically re-deployed using the `master` image just pushed
* sandbox `pre` will immediately use the new dockerhub image on next run

Github [release](https://help.github.com/en/github/administering-a-repository/about-releases) triggers CI pipeline which:

 * builds the finalized version of application
 * assembles the finalized docker image tagged with a version defined in the release (eg: `v0.1.0`)
 * pushes the finalized release docker image to dockerhub and amazon ecr
 * IS NOT deployed to Amazon `live` env automatically; however, it will be immediately available via `sandbox`

While technically release could be made off any branch, it is always done off `master`. Version is always prefixed with a `v` just as github and versioning semantics suggest.